import React, { useState, useRef, useEffect } from 'react';
import { Send, Bot, User, Sparkles, Copy, Check, RefreshCw } from 'lucide-react';

export interface ChatMessageItem {
  id: string;
  role: 'user' | 'assistant' | 'system';
  content: string;
  timestamp?: string;
}

export interface ChatInterfaceProps {
  initialMessages?: ChatMessageItem[];
  onSendMessage?: (content: string) => Promise<void> | void;
  isLoading?: boolean;
  placeholder?: string;
  className?: string;
}

export const ChatInterface: React.FC<ChatInterfaceProps> = ({
  initialMessages = [
    {
      id: 'welcome-1',
      role: 'assistant',
      content:
        "I am OmniAI, the sentient engine behind DarkMisfitStudios. Ask anything, explore high-concept ideas, or prepare creative directives.",
      timestamp: 'Just now',
    },
  ],
  onSendMessage,
  isLoading = false,
  placeholder = 'Type your message to OmniAI...',
  className = '',
}) => {
  const [messages, setMessages] = useState<ChatMessageItem[]>(initialMessages);
  const [inputValue, setInputValue] = useState('');
  const [copiedId, setCopiedId] = useState<string | null>(null);
  const messagesEndRef = useRef<HTMLDivElement>(null);
  const textareaRef = useRef<HTMLTextAreaElement>(null);

  // Auto-scroll to latest message
  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages, isLoading]);

  const handleSubmit = async (e?: React.FormEvent) => {
    if (e) e.preventDefault();
    const trimmed = inputValue.trim();
    if (!trimmed || isLoading) return;

    const userMessage: ChatMessageItem = {
      id: `usr-${Date.now()}`,
      role: 'user',
      content: trimmed,
      timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
    };

    setMessages((prev) => [...prev, userMessage]);
    setInputValue('');

    if (onSendMessage) {
      await onSendMessage(trimmed);
    } else {
      setTimeout(() => {
        const reply: ChatMessageItem = {
          id: `omni-${Date.now()}`,
          role: 'assistant',
          content: `Omni Core received your prompt: "${trimmed}". Processing through neural pipeline.`,
          timestamp: new Date().toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' }),
        };
        setMessages((prev) => [...prev, reply]);
      }, 600);
    }
  };

  const handleKeyDown = (e: React.KeyboardEvent<HTMLTextAreaElement>) => {
    if (e.key === 'Enter' && !e.shiftKey) {
      e.preventDefault();
      void handleSubmit();
    }
  };

  const copyToClipboard = (id: string, text: string) => {
    navigator.clipboard.writeText(text);
    setCopiedId(id);
    setTimeout(() => setCopiedId(null), 1800);
  };

  return (
    <div className={`flex flex-col h-full bg-[#000000] text-[#f1f5f9] overflow-hidden ${className}`}>
      {/* 1. SCROLLABLE MESSAGE DISPLAY AREA */}
      <div className="flex-1 overflow-y-auto px-4 py-4 space-y-4 no-scrollbar">
        {messages.length === 0 ? (
          <div className="flex flex-col items-center justify-center h-full text-center py-12 px-4">
            <div className="w-12 h-12 rounded-2xl bg-[#0a071b] border border-[#0C00CA]/50 shadow-[0_0_16px_rgba(12,0,202,0.4)] flex items-center justify-center mb-3">
              <Sparkles className="w-6 h-6 text-[#00FFFF]" />
            </div>
            <h3 className="text-base font-bold text-white mb-1">OmniAI Chat Interface</h3>
            <p className="text-xs text-[#948eb5] max-w-xs mb-6">
              Begin dialogue with your shadow companion.
            </p>
            <div className="flex flex-wrap justify-center gap-2 max-w-sm">
              {['Plan a music video', 'Brainstorm creative concepts', 'Explain technical architecture'].map(
                (prompt) => (
                  <button
                    key={prompt}
                    onClick={() => {
                      setInputValue(prompt);
                      textareaRef.current?.focus();
                    }}
                    className="text-xs px-3 py-1.5 rounded-xl bg-[#0a071b] border border-[#1e1642] text-[#f1f5f9] hover:text-[#00FFFF] hover:border-[#0C00CA] transition"
                  >
                    {prompt}
                  </button>
                )
              )}
            </div>
          </div>
        ) : (
          messages.map((msg) => {
            const isUser = msg.role === 'user';
            return (
              <div
                key={msg.id}
                className={`flex gap-3 text-xs sm:text-sm ${
                  isUser ? 'justify-end' : 'justify-start'
                }`}
              >
                {!isUser && (
                  <div className="w-7 h-7 rounded-xl bg-[#0a071b] border border-[#00FFFF]/40 flex items-center justify-center shrink-0 mt-0.5">
                    <Bot className="w-4 h-4 text-[#00FFFF]" />
                  </div>
                )}

                <div
                  className={`relative max-w-[85%] sm:max-w-[75%] rounded-2xl px-4 py-2.5 ${
                    isUser
                      ? 'bg-gradient-to-r from-[#0C00CA] to-[#1a0fe0] text-white font-medium rounded-tr-none shadow-[0_0_16px_rgba(12,0,202,0.4)]'
                      : 'bg-[#0a071b] border border-[#1e1642] text-[#f1f5f9] rounded-tl-none shadow-inner'
                  }`}
                >
                  <p className="whitespace-pre-wrap leading-relaxed">{msg.content}</p>

                  <div
                    className={`flex items-center justify-between gap-3 mt-1.5 text-[10px] ${
                      isUser ? 'text-white/70' : 'text-[#948eb5]'
                    }`}
                  >
                    <span>{msg.timestamp || ''}</span>
                    {!isUser && (
                      <button
                        onClick={() => copyToClipboard(msg.id, msg.content)}
                        className="hover:text-[#00FFFF] transition inline-flex items-center gap-1"
                        title="Copy message"
                      >
                        {copiedId === msg.id ? (
                          <>
                            <Check className="w-3 h-3 text-[#00FFFF]" />
                            <span className="text-[#00FFFF]">Copied</span>
                          </>
                        ) : (
                          <>
                            <Copy className="w-3 h-3" />
                            <span>Copy</span>
                          </>
                        )}
                      </button>
                    )}
                  </div>
                </div>

                {isUser && (
                  <div className="w-7 h-7 rounded-xl bg-[#0a071b] border border-[#0C00CA]/60 flex items-center justify-center shrink-0 mt-0.5">
                    <User className="w-4 h-4 text-[#00FFFF]" />
                  </div>
                )}
              </div>
            );
          })
        )}

        {/* Loading Indicator */}
        {isLoading && (
          <div className="flex gap-3 text-xs justify-start items-center">
            <div className="w-7 h-7 rounded-xl bg-[#0a071b] border border-[#00FFFF]/40 flex items-center justify-center shrink-0">
              <RefreshCw className="w-4 h-4 text-[#00FFFF] animate-spin" />
            </div>
            <div className="rounded-2xl rounded-tl-none px-4 py-2 bg-[#0a071b] border border-[#1e1642] text-[#00FFFF] flex items-center gap-2 animate-pulse font-mono text-xs">
              <span className="w-1.5 h-1.5 rounded-full bg-[#00FFFF] animate-ping" />
              <span>OmniAI is thinking...</span>
            </div>
          </div>
        )}

        <div ref={messagesEndRef} />
      </div>

      {/* 2. INPUT FORM FOR TEXT MESSAGES */}
      <form
        onSubmit={handleSubmit}
        className="p-3 bg-[#000000] border-t border-[#1e1642] shrink-0"
      >
        <div className="flex items-end gap-2 bg-[#0a071b] border border-[#1e1642] focus-within:border-[#0C00CA] focus-within:shadow-[0_0_16px_rgba(12,0,202,0.45)] rounded-2xl p-1.5 transition-all">
          <textarea
            ref={textareaRef}
            value={inputValue}
            onChange={(e) => setInputValue(e.target.value)}
            onKeyDown={handleKeyDown}
            placeholder={placeholder}
            rows={1}
            className="flex-1 bg-transparent border-0 focus:ring-0 text-[#f1f5f9] placeholder-[#948eb5] text-xs sm:text-sm resize-none py-2 px-2 focus:outline-none max-h-28 no-scrollbar"
          />
          <button
            type="submit"
            disabled={!inputValue.trim() || isLoading}
            className="p-2.5 rounded-xl bg-[#0C00CA] hover:bg-[#1a0fe0] text-white font-bold transition shrink-0 disabled:opacity-30 disabled:scale-100 active:scale-95 shadow-[0_0_12px_rgba(12,0,202,0.6)]"
            title="Send Message"
          >
            <Send className="w-4 h-4" />
          </button>
        </div>
      </form>
    </div>
  );
};
